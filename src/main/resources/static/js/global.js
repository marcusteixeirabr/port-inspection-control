/* ============================================================
   GLOBAL.JS — scripts compartilhados entre todas as páginas
   ============================================================ */

/**
 * Relógio em tempo real no rodapé.
 * Procura um elemento #clock na página e atualiza a cada segundo.
 */
(function () {
    const clockEl = document.getElementById('clock');
    if (!clockEl) return; // sai sem erro se não houver #clock na página

    function updateClock() {
        clockEl.textContent = new Date().toLocaleTimeString('pt-BR', {
            timeZone: 'America/Sao_Paulo',
            hour:     '2-digit',
            minute:   '2-digit',
            second:   '2-digit',
            hour12:   false
        }) + ' BRT';
    }

    updateClock();
    setInterval(updateClock, 1000);
})();