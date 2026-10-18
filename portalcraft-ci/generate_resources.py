from pathlib import Path
import json, struct, zlib

ROOT = Path(__file__).parent / 'src' / 'main' / 'resources'
ASSETS = ROOT / 'assets' / 'portalcraft'

def write_json(path, data):
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(data, ensure_ascii=False, indent=2), encoding='utf-8')

def png(path, rgba, size=16):
    path.parent.mkdir(parents=True, exist_ok=True)
    w = h = size
    raw = b''.join(b'\x00' + bytes(rgba) * w for _ in range(h))
    def chunk(kind, data):
        return struct.pack('>I', len(data)) + kind + data + struct.pack('>I', zlib.crc32(kind + data) & 0xffffffff)
    data = b'\x89PNG\r\n\x1a\n'
    data += chunk(b'IHDR', struct.pack('>IIBBBBB', w, h, 8, 6, 0, 0, 0))
    data += chunk(b'IDAT', zlib.compress(raw, 9))
    data += chunk(b'IEND', b'')
    path.write_bytes(data)

write_json(ASSETS/'lang/en_us.json', {
    'item.portalcraft.portal_gun': 'Portal Gun',
    'block.portalcraft.companion_cube': 'Companion Cube',
    'block.portalcraft.white_panel': 'White Test Panel',
    'block.portalcraft.black_panel': 'Black Test Panel',
    'message.portalcraft.no_space': 'Not enough space for portal',
    'message.portalcraft.blue_portal': 'Blue portal placed',
    'message.portalcraft.orange_portal': 'Orange portal placed'
})
write_json(ASSETS/'lang/ru_ru.json', {
    'item.portalcraft.portal_gun': 'Портальная пушка',
    'block.portalcraft.companion_cube': 'Куб-компаньон',
    'block.portalcraft.white_panel': 'Белая тестовая панель',
    'block.portalcraft.black_panel': 'Чёрная тестовая панель',
    'message.portalcraft.no_space': 'Недостаточно места для портала',
    'message.portalcraft.blue_portal': 'Синий портал установлен',
    'message.portalcraft.orange_portal': 'Оранжевый портал установлен'
})
for name in ('white_panel','black_panel','companion_cube'):
    write_json(ASSETS/f'blockstates/{name}.json', {'variants': {'': {'model': f'portalcraft:block/{name}'}}})
    write_json(ASSETS/f'models/block/{name}.json', {'parent': 'minecraft:block/cube_all', 'textures': {'all': f'portalcraft:block/{name}'}})
    write_json(ASSETS/f'models/item/{name}.json', {'parent': f'portalcraft:block/{name}'})
for name in ('blue_portal','orange_portal'):
    variants = {}
    rotations = {'north': {}, 'south': {'y':180}, 'east': {'y':90}, 'west': {'y':270}, 'up': {'x':270}, 'down': {'x':90}}
    for facing, rot in rotations.items():
        variants[f'facing={facing}'] = {'model': f'portalcraft:block/{name}', **rot}
    write_json(ASSETS/f'blockstates/{name}.json', {'variants': variants})
    write_json(ASSETS/f'models/block/{name}.json', {'parent':'minecraft:block/cube_all', 'textures': {'all': f'portalcraft:block/{name}'}})
write_json(ASSETS/'models/item/portal_gun.json', {'parent':'minecraft:item/generated','textures':{'layer0':'portalcraft:item/portal_gun'}})
png(ASSETS/'textures/block/white_panel.png', (224,224,224,255))
png(ASSETS/'textures/block/black_panel.png', (35,35,40,255))
png(ASSETS/'textures/block/companion_cube.png', (165,165,170,255))
png(ASSETS/'textures/block/blue_portal.png', (30,120,255,190))
png(ASSETS/'textures/block/orange_portal.png', (255,125,25,190))
png(ASSETS/'textures/item/portal_gun.png', (220,220,225,255))
print('PortalCraft resources generated')
