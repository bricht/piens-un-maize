 <?php
	
	set_time_limit(20000);
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$date = str_replace("%20", " ", $_GET['date']);


	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}
	
	$sql = "select p_id, p_price from product";
	$result = $conn->query($sql);
	
	if($result->num_rows > 0) {
		echo "resut has" . $result->num_rows . " rows";
		while($row = mysqli_fetch_array($result)){
			
			$sql = "INSERT INTO storeproductprice (spp_storeID, spp_productID, spp_price , spp_last_update)
			VALUES ($s_id, $row[0], $row[1], '$date' )";

			if ($conn->query($sql) === TRUE) {
				echo $s_id . ' ' . $row['p_id'] . ' ' . $row['p_price'] . ' ' . "   Store Product Price record created successfully";
			} else {
				echo "-Error: " . $sql. " " . $conn->error;
			}
			print "\r\n";
		}
	}
	
	$conn->close();
	
?> 