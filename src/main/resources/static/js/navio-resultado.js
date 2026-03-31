/* ============================================================
   NAVIO-RESULTADO.JS
   ============================================================ */

/**
 * Animação de entrada dos cards de resultado.
 *
 * Os cards já aparecem via CSS (animation: fadeUp), mas aqui
 * adicionamos um efeito de "pulso" na barra superior para
 * chamar atenção para o resultado logo após carregar.
 */
(function () {
    const cards = document.querySelectorAll('.resultado-card');
    if (!cards.length) return;

    // Após o CSS terminar a animação de fadeUp (≈ 800ms + delay 450ms),
    // adicionamos a classe que dispara o pulso da barra superior.
    setTimeout(() => {
        cards.forEach((card, i) => {
            setTimeout(() => {
                card.classList.add('pulsou');
            }, i * 200); // escalonado entre os dois cards
        });
    }, 1000);
})();