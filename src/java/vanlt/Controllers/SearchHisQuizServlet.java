/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanlt.Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vanlt.daos.QuizHistoryDAO;
import vanlt.dtos.QuizHistoryDto;
import vanlt.dtos.UserDto;

/**
 *
 * @author AVITA
 */
@WebServlet(name = "SearchHisQuizServlet", urlPatterns = {"/SearchHisQuizServlet"})
public class SearchHisQuizServlet extends HttpServlet {

    private final String URL_STUDENT_PAGE = "historyQuiz";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        UserDto dto = (UserDto) session.getAttribute("USER");
        String subjectId = request.getParameter("subjectID");
        String status = request.getParameter("status");

        try {
            if (dto.getUserName() != null) {
                QuizHistoryDAO dao = new QuizHistoryDAO();
                List<QuizHistoryDto> listHistQ = dao.searchQuizHistoryPaging(dto.getId(), Integer.parseInt(subjectId), Integer.parseInt(status));
                if(session.getAttribute("quizHistoryById") != null){
                    session.removeAttribute("quizHistoryById");
                }
                session.setAttribute("quizHistoryById", listHistQ);
            }
        } catch (SQLException ex) {
            log("Search History: " + ex.getMessage());
        } finally {
            response.sendRedirect(URL_STUDENT_PAGE);
            out.close();
        }
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
