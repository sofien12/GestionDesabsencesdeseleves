
package gestiondesabsenc;
import java.sql.*;
import  java.lang.ClassNotFoundException.*;
import static java.time.Clock.system;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Model
{
   Connection c=null;
   ResultSet rs=null;
   Statement stmt=null;
   static int PrimaireA=1;
   static int PrimiryDate=1;
    static int PrimaireC=1;
   public Model ()
   {    
   }
   public boolean driver() 
   {
       try
   {
  DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
  Class.forName("com.mysql.jdbc.Driver");
  
       return true;
   }
       catch(Exception e)  
   {
       System.out.println("Erreur lors de charegment de pilote "+e.getMessage());
   return false;
   }      
   }
   public boolean Openconnexion()
   {
       try{
           
        String url="jdbc:mysql://localhost:3306/gestion";
        c=DriverManager.getConnection(url,"root","");
           return true;
       }
       catch(Exception e)
       {
           System.out.println("Echec de ouverture de la connexion: "+e.getMessage() );
           return false;
       }
   }
   
   
   public boolean test(String ch , String ch1 )
   {
    boolean verif =false ;
       try 
        {
       
       String Emp="SELECT * FROM `employee` WHERE `Password` = '"+ch1+"' and `Id`='"+ch+"'" ;
       
       ResultSet rs=this.selectExec(Emp);
             if(rs.next())
             {
                 verif = true ;
             }
             else 
             { 
                 verif = false ;
                 
             }
       
       } catch (Exception e) {
           System.out.println("heLLO"+e.getMessage());
       }
       return verif ;
   }
     public boolean CLoseConnexion()
     {
         try {
             c.close();
             return true ;
         }
         catch(Exception e)
         {
             System.out.println("Echec de la fermeture de la connexion : "+e.getMessage());
             return false;
         }
     }
     public void   InsertionDate (JComboBox<String> jj,JComboBox<String> mm,JComboBox<String> aa,int j) throws SQLException
     {
         String Sql ="INSERT INTO `date`(`id_date`, `JJ`, `MM`, `Year`, `Id_cours`) VALUES ('"+PrimiryDate++ +"','"+jj.getSelectedItem()+"','"+mm.getSelectedItem()+"','"+aa.getSelectedItem()+"','"+j+"' ) ";
        int rs=stmt.executeUpdate(Sql);             
         if (rs!=0)
       {
           System.out.println("Date est ajouter seccessivement ...");
           
       }
       else
       {
         System.out.println("Date n' est pas  ajouter ...");  
       }
     }
   public void  insertionCours(int i,JComboBox<String>cr,JComboBox<String>jj,JComboBox<String>mm,JComboBox<String>aa) throws SQLException
   {
      
       int rs=stmt.executeUpdate("INSERT INTO `cours`(`IdCours`, `NomCours`, `Id_Class`) VALUES('"+PrimaireC++ +"','"+cr.getSelectedItem()+"','"+i+"')");
       if (rs!=0)
       {
           System.out.println("Cours est ajouter successivement...");
           
       }
       else
       {
         System.out.println("Cours n' est pas  existe...");  
       }
       String clas =  "SELECT `IdCours` FROM `cours` where `NomCours` = '"+cr.getSelectedItem()+"'";
        
       ResultSet rs1=this.selectExec(clas);
          rs1.next();
          System.out.println("Date : ..");
         InsertionDate(jj,mm,aa,rs1.getInt("IdCours"));
          
   }
       public boolean Insersion (JComboBox<String>Lc,JComboBox<String> Cr,JComboBox<String>jj,JComboBox<String>mm,JComboBox<String>aa ) throws SQLException
     {
         
        String clas =  "SELECT `Id_class` FROM `classe` where `libelleClasse` = '"+Lc.getSelectedItem()+"'";
         ResultSet rs1=this.selectExec(clas);
            rs1.next();
          insertionCours(rs1.getInt("Id_class"),Cr,jj,mm,aa);
      
        
        return true;
     }
     
      void DropAbsence(JComboBox<String> Etudiant) throws SQLException
         {
             
             
             stmt=c.createStatement();
             String Eleve="SELECT * FROM `eleve` WHERE `Nom`= '"+Etudiant.getSelectedItem()+"'";
            ResultSet rs=this.selectExec(Eleve);
                   rs.next();
             
             String Absence ="DELETE FROM `absence` WHERE `Id_Eleve`='"+rs.getInt("Id_Eleve")+"' ";
             
             int rA= stmt.executeUpdate(Absence);
             if (rA !=0)
             {
                 JOptionPane.showMessageDialog(null,"annulation d'une absence  ");
                 
             }
                
         }
      
      public void InsertAbsence(DefaultTableModel m,JLabel clss,JLabel Cours,JLabel JJ,JLabel MM,JLabel AA) throws SQLException
      {
          String Class="SELECT `Id_class` FROM `classe` WHERE `libelleClasse` ='"+clss.getText()+"' " ;
          int verif=1;
            ResultSet rC=this.selectExec(Class);
                rC.next();
       int PrimaireCou=PrimaireC-1;
       int PrimaireDate=PrimiryDate-1;
           
           
                     for (int i=0;i<m.getRowCount();i++)
                      {
                          
                            String Id_Eleve=m.getValueAt(i, 0).toString();
                            String j=m.getValueAt(i, 2).toString();
                           if (j.equals("A"))
                          {
 String requette="INSERT INTO `absence`(`idAbsence`, `Id_Eleve`, `idCours`, `Id_date`, `Id_C`) VALUES ('"+PrimaireA++ +"','"+Id_Eleve+"', '"+PrimaireCou+"' , '"+PrimaireDate +"' , '"+rC.getInt("Id_class")+"' )";
                              
                            stmt=c.createStatement();
                            int rs =stmt.executeUpdate(requette);
                            if (rs!=0)
                                verif=0;
                           
                          }
                    }
           if (verif == 0)
               JOptionPane.showMessageDialog(null,"Le presence est seccesivement ...");
       }
     public ResultSet selectExec(String Sql)
     {
         try
         {
             stmt=c.createStatement();
             rs=stmt.executeQuery(Sql);
             
         }catch (Exception e)
         {
            System.out.println("Echec de l'execution de la requette  : "+e.getMessage()); 
         }
         return rs;
     }

   
   
        
}
