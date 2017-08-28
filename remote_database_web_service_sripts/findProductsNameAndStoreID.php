 <?php
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_name = str_replace("%20", " ", $_GET['p_name']);
	
	
	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "select store.*, product.*, storeproductprice.spp_price, storeproductprice.spp_last_update 
			from
			store, product, storeproductprice
            where product.p_name like '%$p_name%' and store.s_id = $s_id
			limit 200;";
	$result = $conn->query($sql);	
	if($result) {
		$jsonData = array();
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$jsonData[] = $row;
			}
			echo json_encode($jsonData);
			} else {
				echo "[]";
			}
	} else {
		echo $conn->error;
	}

	$conn->close();
	
?> 