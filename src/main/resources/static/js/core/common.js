function handleLocaleLinks() {
    let langMenu = document.querySelector('#lang-menu');
    if (langMenu) {
        langMenu.querySelectorAll('ul li').forEach(function (e) {
            let link = e.querySelector('a');
            let lang = link.dataset.lang;
            link.addEventListener('click', function (e) {
                e.preventDefault();
                document.cookie = 'app-lang=' + lang + '; max-age=31536000; path=/';
                window.location.reload();
            });
        });
    }
}

