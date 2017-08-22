 <?php
 
	$p_id = str_replace("%20", " ", $_GET['p_id']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "select product.*, 
			store.*, 
			storeproductprice.spp_price, storeproductprice.spp_last_update
			from storeproductprice
			JOIN store on storeproductprice.spp_storeID = store.s_id
			JOIN product on storeproductprice.spp_productID = product.p_id
			where spp_productID = $p_id
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