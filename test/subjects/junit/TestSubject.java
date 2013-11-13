/**
 * 
 * 
 * 
 * @author Julien Le Van Suu
 */
package subjects.junit;
import states.*;
import subjects.*;
import static org.junit.Assert.*;
import org.junit.Test;

import config.Config;

import diseases.Disease;

public class TestSubject {
	
	@Test
	public void testChangeState() {
		Subject s = new Subject(Type.HUMAN);
		s.setState(new Healthy());
		
		s.changeState();
		assertEquals(s.getState().toString(),"H");		
		s.setDisease(Disease.H1N1);
		s.changeState();
		assertEquals(s.getState().toString(),"S");
		s.changeState();
		assertEquals(s.getState().toString(),"S");
		for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Sick"));i++)
		{
			s.incubate();
		}
		s.changeState();
		assertEquals(s.getState().toString(),"C");
		for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Contagious"));i++)
		{
			s.incubate();
		}
		s.changeState();
		
		//Si on a mis 100 dans le fichier de config alors c'est certain qu'il est mort
		// J'ai préféré faire un if que mettre une fonction qui modifie le fichier properties car si quelqu'un s'amuse a changer les valeurs au cours du programme...
		if (Config.getProperty("H1N1.Mortality.Human")=="100")
		{
		assertEquals(s.getState().toString(),"D");
		}
		else if (Config.getProperty("H1N1.Mortality.Human")=="0")
		{
		assertEquals(s.getState().toString(),"R");
		}
	}
	
	@Test
	public void testContact() {
		if(Config.getProperty("H1N1.Infection.Pig.Human") == "100")
		{
			Subject s1 = new Subject(Type.HUMAN);
			Subject s2 = new Subject(Type.PIG);
			
		
			s2.setDisease(Disease.H1N1);
			s2.changeState();
			for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Sick"));i++)
			{
				s2.incubate();
			}
			s2.changeState();
			s2.contact(s1);
			assertEquals(s1.getState().toString(), "S");
			assertEquals(s2.getState().toString(), "C");
			
		}
		else if(Config.getProperty("H1N1.Infection.Pig.Human") == "0")
		{
			Subject s1 = new Subject(Type.HUMAN);
			Subject s2 = new Subject(Type.PIG);
			
		
			s2.setDisease(Disease.H1N1);
			s2.changeState();
			for(int i =0;i<Integer.parseInt(Config.getProperty("Human.Incubation.Sick"));i++)
			{
				s2.incubate();
			}
			s2.changeState();
			s1.contact(s2);
			assertEquals(s1.getState().toString(), "H");
			assertEquals(s2.getState().toString(), "C");
		}
	
	}
}


