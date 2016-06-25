<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />

        <link rel="stylesheet" href="/literature/resources/css/style.css" />
        
        <!-- Including the Lobster font from Google's Font Directory -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lobster" />
        
        <!-- Enabling HTML5 support for Internet Explorer -->
        <!--[if lt IE 9]>
          <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    </head>
    
    <body>

        <header>
        </header>
        
        <nav>
            <ul class="fancyNav">
                <li id="home"><a href="/literature" class="homeIcon">Home</a></li>
                <li id="resources"><a href="/literature/resource/list/1">Resources</a></li>
                <li id="authors"><a href="#authors">Authors</a></li>
                <li id="about"><a href="#about">About us</a></li>
                <li id="services"><a href="#services">Services</a></li>
                <li id="contacts"><a href="#contacts">Contacts</a></li>
            </ul>
        </nav>
        
        <footer>
        <div align="center">
		<form action="/literature/search/Amazon" method="POST">
			<input type="text" name="searchQuery" size="90" placeholder=" Enter comma separated keywords or phrases">&nbsp;<input
				type="submit" id="searchButton" value="Search" />
		</form>
	</div>
        </footer>
            
    </body>
</html>
