package sonar.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import sonar.core.integration.fmp.FMPHelper;
import sonar.core.integration.fmp.handlers.TileHandler;
import sonar.core.network.utils.IByteBufTile;

public class PacketByteBufServer extends PacketCoords<PacketByteBufServer> {

	public int id;
	public IByteBufTile tile;
	public ByteBuf buf;

	public PacketByteBufServer() {
	}

	public PacketByteBufServer(IByteBufTile tile, BlockPos pos, int id) {
		super(pos);
		this.tile = tile;
		this.id = id;
	}
			
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.id = buf.readInt();
		this.buf = buf;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(id);
		tile.writePacket(buf, id);
	}

	public static class Handler extends PacketTileEntityHandler<PacketByteBufServer> {

		@Override
		public IMessage processMessage(PacketByteBufServer message, TileEntity tile) {
			if (!tile.getWorld().isRemote) {
				if (tile instanceof IByteBufTile) {
					IByteBufTile packet = (IByteBufTile) tile;
					packet.readPacket(message.buf, message.id);
				} else {
					TileHandler handler = FMPHelper.getHandler(tile);
					if (handler != null && handler instanceof IByteBufTile) {
						((IByteBufTile) handler).readPacket(message.buf, message.id);
					}
				}
			}
			return null;
		}
	}

}