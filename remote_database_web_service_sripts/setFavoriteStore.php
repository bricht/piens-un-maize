<?php
	
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Add favorite store to user(u_id)
	**/
	
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$u_id = str_replace("%20", " ", $_GET['u_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "INSERT INTO favoritestore (fs_userID,fs_storeID)
	VALUES (?,?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ii', $u_id, $s_id);
	
	if($stmt->execute()) {
		echo "777 Favorite store added to user id:" . $u_id;
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 