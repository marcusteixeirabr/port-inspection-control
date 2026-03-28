/* ============================================================
   WELCOME.JS — scripts exclusivos da página inicial
   ============================================================ */

/**
 * Animação escalonada dos cartões com IntersectionObserver.
 * Cada cartão tem um data-delay (ms) que controla o atraso
 * individual dentro do grupo.
 */
(function () {
    const cards = document.querySelectorAll('.card');
    if (!cards.length) return;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const delay = parseInt(entry.target.dataset.delay) || 0;
                setTimeout(() => {
                    entry.target.classList.add('visible');
                }, delay);
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.15 });

    cards.forEach(card => observer.observe(card));
})();