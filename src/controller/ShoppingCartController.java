package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.beans.BeanConnexion;
import model.beans.EditionBean;
import model.beans.ShoppingCartBean;
import model.classes.Edition;

@WebServlet(name = "shoppingCartController", urlPatterns = {"/shoppingcart"})
public class ShoppingCartController extends HttpServlet {

    private static final String SHOPPINGCART_ROUTE = "/WEB-INF/jsp/shoppingcart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // début copié/collé
        // vérifié si un beanConnexion est enregistre ds la session; si non, le cree 
        HttpSession session = request.getSession();
        BeanConnexion bc = (BeanConnexion) session.getAttribute("sessionConnexion");
        if (bc == null) {
            bc = new BeanConnexion();
            session.setAttribute("sessionConnexion", bc);
        }
        // fin copié/collé

        // get le panier à partir de la session.
        ShoppingCartBean cart = (ShoppingCartBean) session.getAttribute("cart");

        final Map<String, Edition> cartMap = new HashMap<>();
        String prix = null;

        EditionBean eb = new EditionBean();
//        cart.create("978-2-0001-0001-0", eb.findByIsbn(bc, "978-2-0001-0001-0"));
//        cart.create("978-2-0002-0002-0", eb.findByIsbn(bc, "978-2-0002-0002-0"));

        if (cart == null) {
            // Si l'utilisateur n'a pas de panier, le creer.
            cart = new ShoppingCartBean(bc, cartMap);
            cart.create("978-2-0001-0001-0", eb.findByIsbn(bc, "978-2-0001-0001-0"));
            cart.create("978-2-0002-0002-0", eb.findByIsbn(bc, "978-2-0002-0002-0"));
            session.setAttribute("cart", cart);
        } else {
            //cart.setMap(cartMap);
            //session.setAttribute("cart", cart);
        }
        
        
        System.out.println(cart.list());

        if (request.getParameter("add") != null) {
            cart.create(request.getParameter("add"), eb.findByIsbn(bc, request.getParameter("add")));
            
            
            
            //ajouter le prix dans le shopping cart
            
            prix = cart.prix(request.getParameter("add"), eb.findByIsbn(bc, request.getParameter("add")));
            session.setAttribute("prix", prix);
            session.setAttribute("cart", cart);
        }
        if (request.getParameter("inc") != null) {
            cart.inc(request.getParameter("inc"), eb.findByIsbn(bc, request.getParameter("add")));
            prix = cart.prix(request.getParameter("inc"), eb.findByIsbn(bc, request.getParameter("inc")));
            session.setAttribute("prix", prix);
            session.setAttribute("cart", cart);
        }
        if (request.getParameter("dec") != null) {
            cart.dec(request.getParameter("dec"), eb.findByIsbn(bc, request.getParameter("add")));
            prix = cart.prix(request.getParameter("dec"), eb.findByIsbn(bc, request.getParameter("dec")));
            session.setAttribute("prix", prix);
            session.setAttribute("cart", cart);
        }
        if (request.getParameter("del") != null) {
            cart.del(request.getParameter("del"));
            prix = cart.prix(request.getParameter("del"), eb.findByIsbn(bc, request.getParameter("del")));
            session.setAttribute("prix", prix);
            session.setAttribute("cart", cart);
        }
        if (request.getParameter("clean") != null) {
            cart.clean();
            prix = cart.prix(request.getParameter("clean"), eb.findByIsbn(bc, request.getParameter("clean")));
            session.setAttribute("prix", prix);
            session.setAttribute("cart", cart);
        }

        // à l'arrache pour le moment
        if (request.getParameter("save") != null) {
            session.setAttribute("cart", cart);
        }
        if (request.getParameter("load") != null) {
            ShoppingCartBean cartL = (ShoppingCartBean) session.getAttribute("cart"); // faut il le mettre dans un objet ? sais pas. 
            // tu le sais toi ? moi non.
            // moi non plus alors...fait le au feeling ! 
        }
        
        request.getRequestDispatcher(SHOPPINGCART_ROUTE).include(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
