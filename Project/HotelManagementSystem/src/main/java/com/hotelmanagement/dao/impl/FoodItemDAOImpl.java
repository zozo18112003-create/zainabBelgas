package com.hotelmanagement.dao.impl;

import com.hotelmanagement.dao.FoodItemDAO;
import com.hotelmanagement.entity.FoodItem;
import com.hotelmanagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FoodItemDAOImpl implements FoodItemDAO {

    @Override
    public void save(FoodItem foodItem) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(foodItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(FoodItem foodItem) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(foodItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(FoodItem foodItem) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(foodItem);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public FoodItem findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(FoodItem.class, id);
        }
    }

    @Override
    public List<FoodItem> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from FoodItem", FoodItem.class).list();
        }
    }
}
