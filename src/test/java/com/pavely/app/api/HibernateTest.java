package com.pavely.app.api;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.pavely.app.db.Elements;
import com.pavely.app.db.Line;
import com.pavely.app.db.LinesDAO;

public class HibernateTest{
	
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();	
	LinesDAO linesDAO = new LinesDAO();
	
	@Test
	public void getAllAlLinesViaJoinWithElementsTableUpTo400nm(){
		Session session = sessionFactory.openSession();
		double wlTo = 400;
		session.enableFilter("wavelengthToFilter").setParameter("wavelengthTo", wlTo);
		
		Elements al = (Elements) session.get(Elements.class, "Al");
		assertEquals(13, al.getAtomicNumber());
		assertEquals("Al", al.getAtomicName());
		session.close();
	}
	
	@Test
	public void getHydrodenNumber(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Elements h = (Elements) session.get(Elements.class,"H");
		assertEquals(1, h.getAtomicNumber());
		assertEquals("H", h.getAtomicName());
		session.close();
	}
	
	@Test
	public void getAllAlLinesViaJoinWithElementsTable(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Elements al = (Elements) session.get(Elements.class, "Al");
		assertEquals(13, al.getAtomicNumber());
		assertEquals("Al", al.getAtomicName());
		assertEquals(199, al.getLines().size());
		session.close();
	}
	
	@Test
	public void getAllAlLinesNoJoin(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", "Al");
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		assertEquals(199, list.size());
		session.close();
	}
	
	
	@Test
	public void getNeutraAllLinesOnly(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("ionisationFilter").setParameter("ionLevel", "I");
		session.enableFilter("elementFilter").setParameter("elementName", "Al");
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		assertEquals(58, list.size());
		session.close();
	}	
	
	@Test
	public void getElementNameOfListOfLines(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("ionisationFilter").setParameter("ionLevel", "I");
		session.enableFilter("elementFilter").setParameter("elementName", "Al");
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		assertEquals("Al", ((Line) list.get(0)).getElement().getAtomicName());
		session.close();
	}
	
	@Test
	public void getLinesByList(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<String> elements = Arrays.asList("H", "Al");
		session.enableFilter("listOfElementsFilter").setParameterList("elementNames", elements);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		assertNotNull(list);
		boolean h = false, al = false;
		for (int i = 0; i < list.size(); i++){
			String el = ((Line) list.get(i)).getElement().getAtomicName();
			if (el.equals("H")) {
				h = true;
			}		
			if (el.equals("Al")) {
				al = true;
			} 
		}
		assertTrue(al);
		assertTrue(h);
		session.close();
	}
	
	@Test
	public void getLinesByListForWlFromAndTo(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<String> elements = Arrays.asList("Al", "Ca");
		double wlFrom = 400;
		double wlTo = 500;
		session.enableFilter("listOfElementsFilter").setParameterList("elementNames", elements);
		session.enableFilter("wavelengthFromFilter").setParameter("wavelengthFrom", wlFrom);
		session.enableFilter("wavelengthToFilter").setParameter("wavelengthTo", wlTo);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		assertNotNull(list);	
		assertEquals(list.size(), 43);
		session.close();
	}
	
	@Test 
	public void getLinesByElementsAndStartWl(){
		double wlFrom = 400;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", "Al");
		session.enableFilter("wavelengthFromFilter").setParameter("wavelengthFrom", wlFrom);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		double minWl = 1000;
		for (int i = 0; i < list.size(); i++){
			double wl = ((Line) list.get(i)).getWl(); 
			minWl = wl < minWl ? wl : minWl;
		}

		assertTrue(minWl >= wlFrom);
		session.close();
	}
	
	@Test 
	public void getLinesByElementAndEndWl(){
		double wlTo = 800;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", "Al");
		session.enableFilter("wavelengthToFilter").setParameter("wavelengthTo", wlTo);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		double maxWl = 200;
		for (int i = 0; i < list.size(); i++){
			double wl = ((Line) list.get(i)).getWl(); 
			maxWl = wl > maxWl ? wl : maxWl;
		}

		assertTrue(maxWl <= wlTo);
		session.close();
	}


	@Test
	public void getLinesByElementAndEndWlViaDAO(){
		List<Line> list = linesDAO.getLinesByElementAndEndWl("A", 800);
		double maxWl = 200;
		for (int i = 0; i < list.size(); i++){
			double wl = ((Line) list.get(i)).getWl(); 
			maxWl = wl > maxWl ? wl : maxWl;
		}
		assertTrue(maxWl <= 800);
	}
	
	@Test
	public void getLinesByElementAndStartWlViaDAO(){
		List<Line> list = linesDAO.getLinesByElementAndEndWl("A", 200);
		double minWl = 1000;
		for (int i = 0; i < list.size(); i++){
			double wl = ((Line) list.get(i)).getWl(); 
			minWl = wl < minWl ? wl : minWl;
		}
		assertTrue(minWl >= 200);
	} 
}