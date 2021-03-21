package vanlt.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import vanlt.conn.MyConnection;
import vanlt.dtos.QuizHistoryDto;

/**
 *
 * @author AVITA
 */
public class QuizHistoryDAO {

    public QuizHistoryDAO() {
        
    }
    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public void addEntry(int studentId, int numOfQuiz, int correctAnswer, Date dateQuiz, int subjectID) throws Exception {
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement("insert into QuizHistory (studentId, numOfQuiz, correctAnswer, dateQuiz, subjectid) values (?,?,?,?,?)");
            preStm.setInt(1, studentId);
            preStm.setInt(2, numOfQuiz);
            preStm.setInt(3, correctAnswer);
            preStm.setDate(4, dateQuiz);
            preStm.setInt(5, subjectID);
            preStm.execute();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeConnection();
        }
    }

    public List<QuizHistoryDto> getAllEntry() throws Exception {
        List<QuizHistoryDto> history = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement("Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id");
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                int quizNum = rs.getInt("numOfQuiz");
                int subId = rs.getInt("subjectid");
                int correctAns = rs.getInt("correctAnswer");
                Date date = rs.getDate("dateQuiz");
                history.add(new QuizHistoryDto(id, username, quizNum,subId, correctAns, date));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeConnection();
        }
        return history;
    }
    public List<QuizHistoryDto> getHis(int userID) throws Exception {
        List<QuizHistoryDto> history = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement("Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = ?");
            preStm.setInt(1, userID);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                int quizNum = rs.getInt("numOfQuiz");
                int subId = rs.getInt("subjectid");
                int correctAns = rs.getInt("correctAnswer");
                Date date = rs.getDate("dateQuiz");
                history.add(new QuizHistoryDto(id, username,  quizNum, subId, correctAns, date));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeConnection();
        }
        return history;
    }
    public List<QuizHistoryDto> searchQuizHistoryPaging(int userid, int subjectID,int status ) throws SQLException{
        List<QuizHistoryDto> history = new ArrayList<>();
        int count = 2;
        try {
            String sql = "Select q.*, u.username from QuizHistory q, Users u where q.studentId = u.id and q.studentId = ?  ";
            if (subjectID != 0) { 
                sql += "and q.subjectid = ? ";
            }
            if (status == 1) { 
                sql += "and q.correctAnswer >= (q.numOfQuiz/2) ";
            }
            if (status == 2) { 
                sql += "and q.correctAnswer < (q.numOfQuiz/2) ";
            }
            conn = MyConnection.getMyConnection(); 
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userid);

            if (subjectID != 0) {
                preStm.setInt(count, subjectID);
                count++;
            }
           
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                int quizNum = rs.getInt("numOfQuiz");
                int subId = rs.getInt("subjectid");
                int correctAns = rs.getInt("correctAnswer");
                Date date = rs.getDate("dateQuiz");
                
                history.add(new QuizHistoryDto(id, username,  quizNum, subId, correctAns, date));
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return history;
    }

    
}
