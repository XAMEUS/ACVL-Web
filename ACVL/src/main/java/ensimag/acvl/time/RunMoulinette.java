/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ensimag.acvl.time;

import ensimag.acvl.controller.Controller;
import ensimag.acvl.dao.RegistrationDAO;

/**
 *
 * @author bouvipie
 */
public class RunMoulinette extends Controller implements Runnable {

    @Override
    public void run() {
        System.out.println("hjohjohj");
        //RegistrationDAO registrationDAO = new RegistrationDAO(ds);
        //registrationDAO.moulinette();
    }
}
