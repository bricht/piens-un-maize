 <?php
 
	$s_name = str_replace("%20", " ", $_GET['s_name']);
	$s_location = str_replace("%20", " ", $_GET['s_location']);
	$u_id = str_replace("%20", " ", $_GET['u_id']);
	
	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "select distinct store.*
	from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id 
            JOIN favoritestore on favoritestore.fs_storeID = store.s_id
			JOIN product on storeproductprice.spp_productID = product.p_id
			where store.s_name like '%$s_name%' and store.s_location like '%$s_location%' and favoritestore.fs_userID = $u_id
			limit 200";
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
		echo "-Error: sql query failed!";
	}

	$conn->close();
?> 