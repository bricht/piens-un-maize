 <?php
	
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Reutrn products where product barcode is equals 'barcode'
	**/
	
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	$p_id = str_replace("%20", " ", $_GET['p_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "delete from favoriteproduct  
	where 
	fp_productID = ? 
	and 
	fp_userID = ?
	";

	$stmt = $conn->prepare($sql);
	$stmt->bind_param('ii', $p_id, $u_id);
	if($stmt->execute()) {
		echo "Rows affected: " . $conn->affected_rows;
	} else {
		echo "Error: sql query failed!" . $conn->error;
	}
	
	$stmt->close();
	$conn->close();
	
?> 