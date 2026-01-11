// --- SHARED LOGIC (MOCK DATABASE) ---
const DB = {
    getReservations: () => JSON.parse(localStorage.getItem('hms_reservations')) || [],
    addReservation: (res) => {
        const list = DB.getReservations();
        list.push(res);
        localStorage.setItem('hms_reservations', JSON.stringify(list));
    },
    // Mock initial data if empty
    init: () => {
        if (!localStorage.getItem('hms_reservations')) {
            const initial = [
                { id: 101, guest: 'Alice Wonderland', room: 'Deluxe Suite', checkIn: '2026-02-10', nights: 2, total: 700, status: 'Confirmed' }
            ];
            localStorage.setItem('hms_reservations', JSON.stringify(initial));
        }
    }
};

DB.init();

// --- CLIENT SIDE LOGIC (index.html) ---

// Search functionality
const searchInput = document.getElementById('roomSearch');
if (searchInput) {
    searchInput.addEventListener('keyup', (e) => {
        const term = e.target.value.toLowerCase();
        document.querySelectorAll('.room-item').forEach(item => {
            const type = item.getAttribute('data-type').toLowerCase();
            item.style.display = type.includes(term) ? 'block' : 'none';
        });
    });
}

// Booking Modal Logic
function openBookingModal(roomType, price) {
    const modal = new bootstrap.Modal(document.getElementById('bookingModal'));
    document.getElementById('modalRoomType').value = roomType;
    document.getElementById('displayRoomType').value = roomType;
    document.getElementById('modalPrice').value = price;

    document.getElementById('pricePerNight').innerText = price;
    document.getElementById('totalPrice').innerText = '$0';
    document.getElementById('nightCount').innerText = '0';

    // Set Min Date to Today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('checkInDate').setAttribute('min', today);
    document.getElementById('checkOutDate').setAttribute('min', today);

    modal.show();
}

function calculateTotal() {
    const checkIn = new Date(document.getElementById('checkInDate').value);
    const checkOut = new Date(document.getElementById('checkOutDate').value);
    const price = parseInt(document.getElementById('modalPrice').value);

    if (checkIn && checkOut && checkOut > checkIn) {
        const diffTime = Math.abs(checkOut - checkIn);
        const nights = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

        document.getElementById('nightCount').innerText = nights;
        document.getElementById('totalPrice').innerText = '$' + (nights * price);
    } else {
        document.getElementById('nightCount').innerText = '0';
        document.getElementById('totalPrice').innerText = '$0';
    }
}

// Listeners for Date Changes
const checkInInput = document.getElementById('checkInDate');
const checkOutInput = document.getElementById('checkOutDate');

if (checkInInput && checkOutInput) {
    checkInInput.addEventListener('change', calculateTotal);
    checkOutInput.addEventListener('change', calculateTotal);
}

// Handle Form Submit
const bookingForm = document.getElementById('bookingForm');
if (bookingForm) {
    bookingForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const firstName = document.getElementById('guestFirstName').value;
        const lastName = document.getElementById('guestLastName').value;
        const nights = document.getElementById('nightCount').innerText;

        if (nights === '0') {
            alert('Please select valid dates.');
            return;
        }

        const reservation = {
            id: Date.now(),
            guest: firstName + ' ' + lastName, // Combine names
            room: document.getElementById('modalRoomType').value,
            checkIn: document.getElementById('checkInDate').value,
            nights: nights,
            total: document.getElementById('totalPrice').innerText,
            status: 'Pending'
        };

        DB.addReservation(reservation);

        alert('Reservation Successful! We look forward to seeing you, ' + firstName + '.');
        const modal = bootstrap.Modal.getInstance(document.getElementById('bookingModal'));
        modal.hide();
        bookingForm.reset();
    });
}


// --- ADMIN SIDE LOGIC (admin.html) ---

// Render Reservations in Admin Dashboard
function renderReservations() {
    const tbody = document.getElementById('reservations-body');
    if (!tbody) return;

    // Check Login (Simple Session Check)
    if (!sessionStorage.getItem('isAdmin')) {
        window.location.href = 'login.html';
        return;
    }

    const list = DB.getReservations();
    tbody.innerHTML = list.map(r => `
        <tr>
            <td>#${r.id.toString().slice(-4)}</td>
            <td class="fw-bold">${r.guest}</td>
            <td>${r.room}</td>
            <td>${r.checkIn} (${r.nights} nights)</td>
            <td class="fw-bold text-success">${r.total}</td>
            <td><span class="badge ${r.status === 'Confirmed' ? 'bg-success' : 'bg-warning text-dark'}">${r.status}</span></td>
            <td>
                <button class="btn btn-sm btn-outline-success" onclick="updateStatus(${r.id}, 'Confirmed')"><i class="bi bi-check-lg"></i></button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteRes(${r.id})"><i class="bi bi-trash"></i></button>
            </td>
        </tr>
    `).join('');
}

function updateStatus(id, newStatus) {
    let list = DB.getReservations();
    const idx = list.findIndex(r => r.id === id);
    if (idx !== -1) {
        list[idx].status = newStatus;
        localStorage.setItem('hms_reservations', JSON.stringify(list));
        renderReservations();
    }
}

function deleteRes(id) {
    if (confirm('Delete this reservation?')) {
        let list = DB.getReservations();
        list = list.filter(r => r.id !== id);
        localStorage.setItem('hms_reservations', JSON.stringify(list));
        renderReservations();
    }
}

// --- INITIALIZATION ---
document.addEventListener('DOMContentLoaded', () => {
    // Determine which page we are on
    if (document.getElementById('reservations-body')) {
        renderReservations(); // Admin Page
    }
});

// Helper for switching admin sections (re-using old logic)
function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(el => el.classList.add('d-none'));
    document.getElementById(sectionId).classList.remove('d-none');
}
