# Pick-You-Browser
With recent Android changes forcing a single default browser, users lose flexibility. This app restores freedom, letting users pick the browser that fits each link, giving full control over their browsing experience.
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browser Choice App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f8fa;
            margin: 0;
            padding: 0;
            color: #24292e;
        }
        .container {
            max-width: 900px;
            margin: auto;
            padding: 2rem;
        }
        header {
            display: flex;
            align-items: center;
            margin-bottom: 2rem;
        }
        header img {
            width: 80px;
            height: 80px;
            margin-right: 1rem;
        }
        h1 {
            margin: 0;
            font-size: 2rem;
            color: #0366d6;
        }
        .badge {
            display: inline-block;
            background-color: #28a745;
            color: white;
            padding: 0.2rem 0.5rem;
            font-size: 0.8rem;
            border-radius: 6px;
            margin-left: 10px;
            vertical-align: middle;
        }
        section {
            margin-bottom: 2rem;
        }
        section h2 {
            border-bottom: 1px solid #d1d5da;
            padding-bottom: 0.5rem;
        }
        img.app-screenshot {
            width: 100%;
            max-width: 600px;
            border: 1px solid #d1d5da;
            border-radius: 6px;
        }
        a {
            color: #0366d6;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        code {
            background-color: #f6f8fa;
            padding: 0.2rem 0.4rem;
            border-radius: 4px;
            font-family: monospace;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <img src="https://via.placeholder.com/80" alt="App Logo">
            <h1>Browser Choice App <span class="badge">v1.0</span></h1>
        </header>

        <section>
            <h2>About</h2>
            <p>
                Android has increasingly forced users to set a single default browser, removing the freedom to choose a browser per link. 
                This app restores that flexibility, allowing users to select the browser that best suits their needs for each link, 
                giving them full control over their browsing experience.
            </p>
            <img src="https://via.placeholder.com/600x400" alt="App Screenshot" class="app-screenshot">
        </section>

        <section>
            <h2>Features</h2>
            <ul>
                <li>Pick your preferred browser for each link.</li>
                <li>Bypass Android’s single default browser restriction.</li>
                <li>Simple and intuitive interface.</li>
                <li>Lightweight and efficient.</li>
            </ul>
        </section>

        <section>
            <h2>Installation</h2>
            <p>
                Clone the repository and build the app with Android Studio:
            </p>
            <pre><code>git clone https://github.com/username/browser-choice-app.git
cd browser-choice-app
./gradlew build</code></pre>
        </section>

        <section>
            <h2>Usage</h2>
            <p>
                Once installed, simply tap any link and choose your preferred browser from the in-app chooser. 
                You can set different browsers for different types of links according to your preference.
            </p>
        </section>

        <section>
            <h2>License</h2>
            <p>
                This project is licensed under the <a href="https://opensource.org/licenses/MIT">MIT License</a>.
            </p>
        </section>
    </div>
</body>
</html>
