package com.pavely.app.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class LinesDAO {
	
	public List<Line> getLinesByElementAndIon(String element, String ion){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("ionisationFilter").setParameter("ionLevel", ion);
		session.enableFilter("elementFilter").setParameter("elementName", element);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();	
		session.close();
		return (List<Line>) list;
	}
	
	
	public List<Line> getLinesByElement(String element){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", element);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();	
		session.close();
		return (List<Line>) list;
	}
	
	public List<Line> getLinesByElementList(List<String> elements){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("listOfElementsFilter").setParameterList("elementNames", elements);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();	
		session.close();
		return (List<Line>) list;
	}
	
	public List<Line> getLinesByElementListAndWlFromAndWlTo(List<String> elements, double wlFrom, double wlTo){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.enableFilter("listOfElementsFilter").setParameterList("elementNames", elements);
		session.enableFilter("wavelengthFromFilter").setParameter("wavelengthFrom", wlFrom);
		session.enableFilter("wavelengthToFilter").setParameter("wavelengthTo", wlTo);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();
		session.close();
		return (List<Line>) list;
	}	
	
	
	public List<Line> getLinesByElementAndStartWl(String element, double wlFrom){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", element);
		session.enableFilter("wavelengthFromFilter").setParameter("wavelengthFrom", wlFrom);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();	
		session.close();
		return (List<Line>) list;
	}
	
	public List<Line> getLinesByElementAndEndWl(String element, double wlTo){
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.enableFilter("elementFilter").setParameter("elementName", element);
		session.enableFilter("wavelengthToFilter").setParameter("wavelengthTo", wlTo);
		Query query = session.createQuery("from Line");
		List<?> list = query.list();	
		session.close();
		return (List<Line>) list;
	}

}
