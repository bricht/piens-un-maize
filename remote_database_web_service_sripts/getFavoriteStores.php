 <?php
	
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return users favorite stores.
	**/
	
	$u_id = str_replace("%20", " ", $_GET['u_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select  
			store.* 
			from favoritestore
			JOIN store on favoritestore.fs_storeID = store.s_id
			JOIN user on favoritestore.fs_userID = user.u_id
			where fs_userID = ?
			limit 200";
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('i', $u_id);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	$stmt->close();
	$conn->close();
?> 