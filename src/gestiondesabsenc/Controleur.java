
package gestiondesabsenc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Controleur {
    String user;
    String password;
    //constrocteur pour employee
    public Controleur(String user,String pass)
    {
        this.user=user;
        password=pass;
        
    }
    //contrecteur vide 
    public Controleur()
    {
        
        
    }
    // verification des liste vide ou null
    public boolean verificationLesListes(JComboBox<String> Cr,JComboBox<String> LC,JComboBox<String> jj,JComboBox<String> mm,JComboBox<String> aa) throws SQLException
    {
        if (jj.getSelectedIndex()==0 && mm.getSelectedIndex()==0 && aa.getSelectedIndex()==0)
        {JOptionPane.showMessageDialog(null,"le Date est obligatoire ");
                return false;
        }
        else
            if (jj.getSelectedIndex()==0)
            {           JOptionPane.showMessageDialog(null,"le jour  est obligatoire ");
            return false;
                    }
            else
            if(mm.getSelectedIndex()==0 )
            {
                JOptionPane.showMessageDialog(null,"le mois est obligatoire ");
                return false;
            }
        
        else
            if (aa.getSelectedIndex()==0)
            {
                JOptionPane.showMessageDialog(null,"l'annee est obligatoire ");
                return false;
            }
          
        else
            if (LC.getSelectedIndex()==0 && Cr.getSelectedIndex()==0 )
            {
                JOptionPane.showMessageDialog(null,"Les classes  est obligatoire  ");
                return false;
            }
            else
                if (LC.getSelectedIndex()==0)
                {
                    JOptionPane.showMessageDialog(null,"Les classes  est obligatoire  ");
                    return false;
                }
                else
                    if (Cr.getSelectedIndex()==-1)
                    {
                        JOptionPane.showMessageDialog(null,"Le Cours   est obligatoire  ");
                        return false;
                    }
           
        
        return true;       
    }
    
   //verification d'insert les Classe
     public boolean verifINSertClasse(JComboBox<String> Lc,JComboBox<String> Cr,JComboBox<String>jj,JComboBox<String>mm,JComboBox<String>aa) throws SQLException
    {
        
        Model m=new Model();
        m.driver();
        m.Openconnexion();
     
    boolean test =m.Insersion(Lc,Cr,jj,mm,aa);
    m.CLoseConnexion();
    if (test == true )
             return true;
         return false;
    }
     //verification de la liste..
   
    //connexion de l'employee
     
        
      ///test pour Employee
    public boolean  testvide() throws SQLException
    {
        Traitement t=new Traitement();
        if (user.equals("") && password.equals(""))
        {
            JOptionPane.showMessageDialog(null,"les champs est obligatoire ");
            return false;
        }   
        else
            if(user.equals(""))
            { 
                JOptionPane.showMessageDialog(null,"UserName svp ");
                return false;
            }
            else
            if(password.equals(""))
            {
                    JOptionPane.showMessageDialog(null,"le Password svp ");
                       return false; 
            }
    else
            {
                if (t.testconnexion(user,password)==false)
                {
                   
                   return false;
                }
           
          }
        return true;
    }

   
  boolean verificationComboxConsultation(JComboBox<String> CCL)
  {
       if (CCL.getSelectedIndex()==0)
        return false;   
      return true ;
  }
      
    void VerifAnulation(JComboBox<String>Cl,JComboBox<String>C) throws SQLException 
    {
        Model m=new Model ();
        m.driver();
        m.Openconnexion();
        String classe="SELECT `Id_class` FROM `classe` WHERE`libelleClasse` ='"+Cl.getSelectedItem()+"' ";
        ResultSet rC=m.selectExec(classe);
        rC.next();
        String Absence ="SELECT * FROM `absence` WHERE `Id_C`='"+rC.getInt("Id_class")+"' ";
        ResultSet rA=m.selectExec(Absence);
        int i=0;
         if (Cl.getSelectedIndex()==1 )
                        {
           
            C.removeAllItems();
            while(rA.next())
                {
                String eleve ="SELECT  `Nom`  FROM `eleve` WHERE  `Id_Eleve`='"+rA.getInt("Id_Eleve")+"'";
        
                ResultSet rs=m.selectExec(eleve); 
                while(rs.next())
                    {
                String n=rs.getString("Nom");
                    C.insertItemAt(n, i);
                 
                         i++;
                    }
            
                }
                        }
           else
               if (Cl.getSelectedIndex()==2)
               {
                   C.removeAllItems();
                   while(rA.next())
                {
                String eleve ="SELECT  `Nom` FROM `eleve` WHERE  `Id_Eleve`='"+rA.getInt("Id_Eleve")+"'";
                ResultSet rs=m.selectExec(eleve); 
                    while(rs.next())
                    {
                        String n=rs.getString("Nom");
                        C.insertItemAt(n, i);
                        i++;
                    }
                }
               }
           else
               if (Cl.getSelectedIndex()==3)
                 {
                     C.removeAllItems();
                     while(rA.next())
                {
                String eleve ="SELECT  `Nom` FROM `eleve` WHERE  `Id_Eleve`='"+rA.getInt("Id_Eleve")+"'";
        
                ResultSet rs=m.selectExec(eleve); 
                    while(rs.next())
                         {
                             String n=rs.getString("Nom");
                            
                             C.insertItemAt(n, i);
                                    i++;
                
                          }
                  }
        
                    }
   
         m.CLoseConnexion();
    }
    boolean verifliset(JComboBox<String> c, JComboBox<String> cr)
    {
         if (c.getSelectedIndex()==0 && cr.getSelectedIndex()==0 )
            {
                JOptionPane.showMessageDialog(null,"Les class est obligatoire  ");
                return false;
            }
            else
                if (c.getSelectedIndex()==0)
                {
                    JOptionPane.showMessageDialog(null,"Les classes  est obligatoire  ");
                    return false;
                }
                else
                    if (cr.getSelectedIndex()==-1)
                    {
                        JOptionPane.showMessageDialog(null,"Etudiant  est obligatoire  ");
                        return false;
                    }
         return true;
    }
       

}

    

