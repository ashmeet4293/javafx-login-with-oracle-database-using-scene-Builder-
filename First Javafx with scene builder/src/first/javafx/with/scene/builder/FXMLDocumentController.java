package first.javafx.with.scene.builder;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import pckg.database.DatabaseConnector;
import pckgsignup.UsedFunctions;
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author ashmeet
 */
public class FXMLDocumentController implements Initializable {

    private Connection conn;
    private OraclePreparedStatement pst;
    private OracleResultSet rs;

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    private Functions functions;
    @FXML
    private Label lblLoginStatus;
    @FXML
    private Button btnLogin;

    private void nextStage(String fxml, String title, boolean resizable) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(resizable);
        stage.show();

    }

    private void handleButtonAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtUsername.getText();
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Login successfull");
            lblLoginStatus.setText("Login Successfull");
        } else {
            lblLoginStatus.setText("Login Failed ");
//            System.out.println("Lofin  Failed ");
        }
    }

    @FXML
    private void signUpAction(ActionEvent event) throws IOException {
        Stage current = (Stage) btnLogin.getScene().getWindow();
        current.hide();
        System.out.println("Sign up Button Pressedd ");
        lblLoginStatus.setText("Sign up Button ");
        nextStage(UsedFunctions.SIGNUP, "Signup", true);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLoginAction(ActionEvent event) throws SQLException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if ((conn = DatabaseConnector.databaseConnection()) != null) {
            String query = "Select username,password from Student where username=? and password = ? ";
            pst = (OraclePreparedStatement) conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            rs=(OracleResultSet) pst.executeQuery();
            if (rs.next()) {
//                System.out.println("Login Successfull ");
                lblLoginStatus.setText("Login Successfull ");
            } else {
//                System.out.println("Login failed ");

                lblLoginStatus.setText("Invalid Login Details ");
            }

        }

//        try {
//            
//            String query = "select username,password from Student where username=? and password=?";
//            pst = (OraclePreparedStatement) conn.prepareStatement(query);
//            pst.setString(1, txtUser.getText());
//            pst.setString(2, pwPassword.getText());
//            rs = (OracleResultSet) pst.executeQuery();
//            if (rs.next()) {
//                System.out.println("Login Successful");
//            } else {
//                System.out.println("Login Failed");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
