/* ============================================================
   INSPECOES.JS
   ============================================================ */

/**
 * Auto-dismiss das flash messages.
 * Após 4 segundos, a mensagem de sucesso/erro some sozinha
 * com uma animação de fade — sem precisar o usuário fechar.
 */
(function () {
    const flashes = document.querySelectorAll('.flash');
    if (!flashes.length) return;

    flashes.forEach(flash => {
        setTimeout(() => {
            flash.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
            flash.style.opacity    = '0';
            flash.style.transform  = 'translateY(-8px)';
            setTimeout(() => flash.remove(), 600);
        }, 4000);
    });
})();