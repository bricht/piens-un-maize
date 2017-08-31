 <?php
 
	/**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Return all users favorite products
	**/
	
	$u_id = str_replace("%20", " ", $_GET['u_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select  
			product.* 
			from favoriteproduct
			JOIN product on favoriteproduct.fp_productID = product.p_id
			JOIN user on favoriteproduct.fp_userID = user.u_id
			where fp_userID = ?";
			
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