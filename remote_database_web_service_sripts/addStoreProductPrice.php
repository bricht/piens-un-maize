 <?php
 
	$s_id = str_replace("%20", " ", $_GET['s_id']);
	$p_id = str_replace("%20", " ", $_GET['p_id']);
	$price = str_replace("%20", " ", $_GET['spp_price']);
	$last_update = str_replace("%20", " ", $_GET['spp_last_update']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "INSERT INTO storeproductprice (spp_storeID, spp_productID, spp_price , spp_last_update)
	VALUES ('$s_id', '$p_id', '$price', '$last_update')";

	if ($conn->query($sql) === TRUE) {
		echo "+New Store Product Price record created successfully";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 