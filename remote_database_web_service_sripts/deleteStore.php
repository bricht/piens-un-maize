 <?php
	
	$s_id = str_replace("%20", " ", $_GET['s_id']);

	$servername = "zesloka.tk";
	$username = "user";
	$password = "parolee";
	$dbname = "piens_un_maize";

	$conn = new mysqli($servername, $username, $password, $dbname);
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	}

	$sql = "delete from storeproductprice where spp_storeID = $s_id;
			delete from store where s_id = $s_id";

	if ($conn->query($sql) === TRUE) {
		echo "Record deleted.";
	} else {
		echo "-Error: " . $sql. " " . $conn->error;
	}

	$conn->close();
	
?> 