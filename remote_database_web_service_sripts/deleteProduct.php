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

	$sql = "SET SQL_SAFE_UPDATES=0;
			delete from product WHERE p_id = 3;
			delete from storeproductprice WHERE spp_productID = 3;
			SET SQL_SAFE_UPDATES=1;";

	if ($conn->query($sql) === TRUE) {
		echo "Sucsess.";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}
	
	$conn->close();
	
?> 