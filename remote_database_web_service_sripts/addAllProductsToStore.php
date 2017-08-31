 <?php
	
	set_time_limit(200000);
	
	$date = str_replace("%20", " ", $_GET['date']);
	
	$loginurl = parse_ini_file('/init/login_url.ini');
	$login = parse_ini_file($loginurl['url']);
	
	$conn = new mysqli($login['server'], $login['username'], $login['password'], $login['database']);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	
	$sql = "select s_id from store";
	$re = $conn->query($sql);
	if($re->num_rows > 0 ) {
		while($ro = mysqli_fetch_array($re)) {
			
			
			$sql = "select p_id, p_price from product";
			$result = $conn->query($sql);
			
			
			$maxPrice = $ro[0] + 0.3;
			$minPrice = $ro[0] - 0.3;
			$newPrice = mt_rand ($minPrice*10, $maxPrice*10) / 10;
			
			
			if($result->num_rows > 0) {
				while($row = mysqli_fetch_array($result)){
					
					$sql = "INSERT INTO storeproductprice (spp_storeID, spp_productID, spp_price , spp_last_update)
							VALUES ($newPrice, $row[0], $row[1], '$date' )";

					if ($conn->query($sql) === TRUE) {
						$counter = $counter + 1;
						echo $counter . "|||" . $ro[0] . ' ' . $row['p_id'] . ' ' . $row['p_price'] . ' ' . "   added" . "\r\n";
					} else {
						echo "-Error: " . $sql. " " . $conn->error;
					}
				}
			}			
		}
	}
	
	$conn->close();
	
?> 