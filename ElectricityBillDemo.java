import java.sql.*;
import  java.util.Scanner;

public class ElectricityBillDemo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/electricity",
                    "root",
                    "1234"
            );

            CallableStatement csInsert = connection.prepareCall("{call insertBill(?,?,?,?)}");
            csInsert.setInt(1, 203

            );
            csInsert.setString(2, "Subhashish");
            csInsert.setInt(3, 350);
            csInsert.setDouble(4, 1750.00);
            csInsert.execute();
            System.out.println("Bill inserted successfully!");
            csInsert.close();


            CallableStatement csSelect = connection.prepareCall("{call selectBill(?,?,?,?)}");
            csSelect.setInt(1, 201);
            csSelect.registerOutParameter(2, Types.VARCHAR);
            csSelect.registerOutParameter(3, Types.INTEGER);
            csSelect.registerOutParameter(4, Types.DOUBLE);
            csSelect.execute();

            String name = csSelect.getString(2);
            int units = csSelect.getInt(3);
            double amount = csSelect.getDouble(4);
            System.out.println("Bill Details -> Name: " + name + ", Units: " + units + ", Amount: " + amount);
            csSelect.close();

            CallableStatement csUpdate = connection.prepareCall("{call updateBillAmount(?,?)}");
            csUpdate.setInt(1, 201);
            csUpdate.setDouble(2, 2000.00);
            csUpdate.execute();
            System.out.println("Bill amount updated successfully!");
            csUpdate.close();


            CallableStatement csDelete = connection.prepareCall("{call deleteBill(?)}");
            csDelete.setInt(1, 201);
            csDelete.execute();
            System.out.println("Bill deleted successfully!");
            csDelete.close();

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
