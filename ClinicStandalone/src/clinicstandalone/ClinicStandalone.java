package clinicstandalone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import clinic.view.*;
import clinic.controller.*;


/**
 *
 * @author Prezes
 */
public class ClinicStandalone {
    
    private static final String PERSISTENCE_UNIT_NAME = "ClinicStandalonePU";
    private static EntityManagerFactory factory;
    

     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
                
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        MultiJpaController controller = MultiJpaController.getInstance(factory);
 
        MainWindow mWindow = new MainWindow(controller);
        mWindow.setController(controller);
        mWindow.setVisible(true);
        
        
        
    }
    
}
