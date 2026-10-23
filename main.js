const { app, BrowserWindow, Menu } = require('electron');
const path = require('path');
const fs = require('fs');

function createWindow() {
  const win = new BrowserWindow({
    width: 1440,
    height: 920,
    minWidth: 900,
    minHeight: 620,
    title: 'PixelForge 3.7',
    backgroundColor: '#17191c',
    autoHideMenuBar: true,
    webPreferences: {
      contextIsolation: true,
      nodeIntegration: false,
      sandbox: true
    }
  });

  const parts = [1, 2, 3, 4].map((n) =>
    fs.readFileSync(path.join(__dirname, `PixelForge.part${n}.html`), 'utf8')
  );
  let html = parts.join('');
  const extensions = [5, 6].map((n) =>
    fs.readFileSync(path.join(__dirname, `PixelForge.part${n}.html`), 'utf8')
  ).join('\n');
  html = html.replace('</body>', `${extensions}\n</body>`);

  const tempHtml = path.join(app.getPath('temp'), 'PixelForge-3.7.html');
  fs.writeFileSync(tempHtml, html, 'utf8');

  win.loadFile(tempHtml).catch((err) => {
    console.error('PixelForge load error:', err);
    win.loadURL('data:text/html;charset=utf-8,' + encodeURIComponent(`<h2 style="font-family:Segoe UI;color:white;background:#17191c;padding:24px">Ошибка загрузки PixelForge<br><small>${String(err)}</small></h2>`));
  });
}

app.whenReady().then(() => {
  Menu.setApplicationMenu(null);
  createWindow();
  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
});
