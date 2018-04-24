package ensimag.acvl.controller;

import ensimag.acvl.controller.Controller;
import ensimag.acvl.dao.RegistrationDAO;


public class RunMoulinette extends Controller implements Runnable {

    @Override
    public void run() {
        if(ensimag.acvl.config.Config.autoMoulinette == true) {
            RegistrationDAO registrationDAO = new RegistrationDAO(ds);
            registrationDAO.moulinette();   
        }
    }
}
