<?php
 
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$u_id = str_replace("%20", " ", $_GET['u_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "INSERT INTO favoriteproduct (fp_userID,fp_productID) VALUES (?,?)";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ii', $u_id, $p_id);
	
	if($stmt->execute()) {
		echo "777 favorite product added";
	} else {
		echo "Error: " . $sql. " " . $conn->error;
	}

	$stmt->close();
	$conn->close();
	
?> 