
package gestiondesabsenc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class Traitement
{
    Traitement ()
    {
        
    }
     public boolean  testconnexion (String user,String password ) throws SQLException
    {
        Model m=new Model() ;
       m.driver();
       m.Openconnexion();
        boolean t =  m.test( user,password) ;
        m.CLoseConnexion();
        return t ;
       }
     
      public void VerificationList (JComboBox<String> Cr,JComboBox<String> LC)
    {
        if (LC.getSelectedIndex()==0)
        {
            Cr.removeAllItems();
        }
         else
            if (LC.getSelectedIndex()==1 )
        {
            Cr.removeAllItems();
            Cr.insertItemAt("Algebre", 0);
            Cr.insertItemAt("Analyse", 1);
            Cr.insertItemAt("Physique",2);
            Cr.insertItemAt("Structure des donnees",3);
            
        }
           else
               if (LC.getSelectedIndex()==2)
               {
                   Cr.removeAllItems();
                   Cr.insertItemAt("Complexite", 0);
                   Cr.insertItemAt("POO",1);
                   Cr.insertItemAt("MCOO", 2);
                   Cr.insertItemAt("PHysique", 3);
                   
               }
           else
               if (LC.getSelectedIndex()==3)
                       {
                   
                   Cr.removeAllItems();
                   Cr.insertItemAt("IHM", 0);
                   Cr.insertItemAt("Systeme repartir", 1);
                   Cr.insertItemAt("DW", 2);
                   
               }
    }
          void remplirTableModel(DefaultTableModel model,JComboBox<String> CCL) throws SQLException 
    {  
       Model m=new Model();
       m.driver();
        m.Openconnexion();
        String SqlClass="SELECT `Id_class` FROM `classe` WHERE `libelleClasse` ='"+CCL.getSelectedItem()+"' ";
        ResultSet rsC=m.selectExec(SqlClass);
        rsC.next();
   String Absence ="SELECT * FROM `absence` WHERE `Id_C`='"+rsC.getInt("Id_class")+"' ";
        ResultSet rA=m.selectExec(Absence);
         while (rA.next())
        {
            String resultat ="SELECT `Id_Eleve`, `Nom`,`NomCours`, `JJ`, `MM`, `Year` FROM  `eleve`,`cours`,`date` WHERE `Id_Eleve`='"+rA.getInt("Id_Eleve")+"' AND `idCours` = '"+rA.getInt("idCours")+"' AND `Id_date` = '"+rA.getInt("Id_date")+" ' ";
            ResultSet rR=m.selectExec(resultat);
            rR.next();
            String Date=rR.getInt("JJ")+"/"+rR.getInt("MM")+"/"+rR.getInt("Year");
            model.insertRow(model.getRowCount(),new Object[]
                            { 
                            rR.getInt("Id_Eleve"),rR.getString("Nom"),rR.getString("NomCours"),
                            Date
            });
        }
         
   m.CLoseConnexion();
       
      }
          
        void resetTableModel(DefaultTableModel model)
        {
                 while (model.getRowCount() > 0)   
        
                  {
                      for (int i=0;i<model.getRowCount();i++)
                      {
                          model.removeRow(i);
                      }
                    }
           }
      
}
