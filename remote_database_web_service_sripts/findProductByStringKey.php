 <?php
 
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Reutrn products where name or category or description is like 'key'
	**/
	
	$str_key = '%' . str_replace("%20", " ", $_GET['str_key']) . '%';

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sql = "select * from product 
			where 
			p_name like ?
			or 
			p_category like ?
			or 
			p_descript like ?
			limit 200";
	$stmt = $conn->prepare($sql);
	$stmt->bind_param('sss', $str_key, $str_key, $str_key);
	
	if($stmt->execute()) {
		echo parseToJSON($stmt);
	} else {
		echo "Error: sql query failed!";
	}
	
	$stmt->close();
	$conn->close();
?> 