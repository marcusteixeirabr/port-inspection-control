/* ============================================================
   SOBRE.JS — scripts exclusivos da página Sobre
   ============================================================ */

/**
 * Scroll reveal das seções de conteúdo.
 * Qualquer elemento com o atributo [data-section] é observado
 * e recebe a classe .visible ao entrar na viewport.
 */
(function () {
    const sections = document.querySelectorAll('[data-section]');
    if (!sections.length) return;

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1 });

    sections.forEach(section => observer.observe(section));
})();