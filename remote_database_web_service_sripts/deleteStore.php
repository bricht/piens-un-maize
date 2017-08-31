 <?php
	
    /**
	/* Created by Guntars Berzins 2017.08.29
	/*
	/* Delete Store instance and all store references in other tables.
	**/
	
	$s_id = str_replace("%20", " ", $_GET['s_id']);

	include($_SERVER['DOCUMENT_ROOT']."piens_un_maize_db/lib/mysqlConnection.php");
	$conn = getMysqlConnection();

	$sqls = array (
		"delete from storeproductprice where spp_storeID = ?",
		"delete from favoritestore where fs_StoreID = ?",
		"delete from store where s_id = ?"
		);
	
	foreach($sqls as &$sql){
		$stmt = $conn->prepare($sql);
		$stmt->bind_param('i', $s_id);
		if($stmt->execute()) {
			echo "Rows affected: " . $conn->affected_rows . "\n\r";
		} else {
			echo "Error: sql query failed!" . $conn->error;
		}
	}
	
	$stmt->close();
	$conn->close();
	
?> 