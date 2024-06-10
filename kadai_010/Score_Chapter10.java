package kadai.kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Score_Chapter10 {

	public static void main(String[] args) {
		
		Connection con = null;
		Statement statement =null;
		
		try {
			// データベースに接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"tritium3T!"
				);
			
			System.out.println("データベースに接続成功");
			
			// 点数データを更新するレコードを取得し、点数順に並べ替えるクエリを準備
			String sql1 = "UPDATE scores SET score_math=95, score_english=80 WHERE id=5";
			statement = con.createStatement();
			
			// 点数データを更新するSQLクエリを実行
			System.out.println("レコードを更新:" + statement.toString());
			int rowCnt = statement.executeUpdate(sql1);
			System.out.println(rowCnt + "件のレコードが更新されました");
			
			// レコードを取得し、点数順に並べ替えるクエリを準備
			String sql2 = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";

			// レコードを取得し、点数順に並べ替えるクエリを実行
			System.out.println("データ取得を実行:" + sql2);
			ResultSet result = statement.executeQuery(sql2);
			
			// SQLクエリの実行結果を抽出
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getNString("name");
				int scoreMath = result.getInt("score_math");
				int scoreEnglish = result.getInt("score_english");
				System.out.println(result.getRow() + "件目:生徒id=" + id + "/氏名=" + name + "/数学=" + scoreMath + "/英語=" + scoreEnglish);
			}
						
		} catch(SQLException e) {
			System.out.println("エラー発生:" + e.getMessage());
		} finally {
			if(statement != null) {
				try {statement.close();} catch(SQLException ignore) {}
			}
			if(con != null) {
				try {con.close();} catch(SQLException ignore) {}
			}
		}
	}

}
