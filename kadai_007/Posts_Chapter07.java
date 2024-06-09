package kadai.kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		// 投稿リスト
		String[][] postList = {
			{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
			{"1002", "2023-02-08", "お疲れ様です！", "12"},
			{"1003", "2023-02-09", "今日も頑張ります！", "18"},
			{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
			{"1002", "2023-02-10", "明日から連休ですね！", "20"}
		};
		
		try {
			// データベースに接続
			con =DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"tritium3T!"
				);
			
			System.out.println("データベースに接続成功");
			
			// 投稿データを追加
			// SQL1クエリを準備
			String sql1 = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES(?, ?, ?, ?);";
			statement = con.prepareCall(sql1);
			
			// リストの1行目から順番に読み込む
			int rowCnt = 0;
			for(int i = 0; i < postList.length; i++) {
				// SQL1のSQLクエリの「?」部分をリストのデータに置き換え	
				statement.setInt(1, Integer.parseInt(postList[i][0]));
				statement.setString(2, postList[i][1]);
				statement.setString(3, postList[i][2]);
				statement.setInt(4,Integer.parseInt(postList[i][3]));
				
				// SQL1クエリを実行 (DBMSに送信)
				System.out.println("レコードの追加" + statement.toString());
				rowCnt = statement.executeUpdate();
				System.out.println(rowCnt + "件のレコードが追加されました");
			}
			
			// 投稿データを検索
			// SQL2クエリを準備
			String sql2 ="SELECT posted_at, post_content, likes FROM posts";
			
			// SQL2のクエリを実行(DBMSの送信)
			ResultSet result = statement.executeQuery(sql2);
			
			// SQL2クエリの実行結果を抽出
			while (result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目:投稿日時=" + postedAt + "/投稿内容=" + postContent + "/いいね数=" + likes);	
			}
		} catch (SQLException e) {
			System.out.println("エラー発生 :" + e.getMessage());
		} finally {
			if (statement != null) {
				try {statement.close();} catch (Exception ignore) {}
			}
			if (con != null) {
				try {con.close();} catch (SQLException ignore) {}
			}
		}
	}

}
