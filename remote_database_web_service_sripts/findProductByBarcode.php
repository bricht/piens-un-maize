 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Reutrn products where product barcode is equals 'barcode'
	**/
 
	$barcode = str_replace("%20", " ", $_GET['b_barcode']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select * from product 
	where p_id in
		(select b_productID from barcode 
		where 
		b_barcode = ?)";
			
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('s', $barcode);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	
	$stmt->close();
	$conn->close();
?> 