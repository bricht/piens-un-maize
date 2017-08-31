 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Delete store reference from user favorite stores
	**/
 
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	$s_id = str_replace("%20", " ", $_GET['s_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "delete from favoritestore  
			where 
			fs_storeID = ? 
			and 
			fs_userID = ?
			";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ii', $s_id, $u_id);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 