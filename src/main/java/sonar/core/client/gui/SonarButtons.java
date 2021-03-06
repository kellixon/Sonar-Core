package sonar.core.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class SonarButtons {

	@SideOnly(Side.CLIENT)
	public static class SonarButton extends GuiButton {

        public boolean isButtonDown;

		public SonarButton(int id, int x, int y, int textureX, int textureY, String display) {
			super(id, x, y, textureX, textureY, display);
		}

		public void onClicked() {
		}

        @Override
		public boolean mousePressed(Minecraft minecraft, int x, int y) {
            isButtonDown = this.enabled && this.visible && x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
			return isButtonDown;
		}

        @Override
		public void mouseReleased(int x, int y) {
			isButtonDown = false;
		}
	}

	public static class HoverButton extends SonarButton {

		public HoverButton(int id, int x, int y, int textureX, int textureY, String display) {
			super(id, x, y, textureX, textureY, display);
		}

		public String getHoverText() {
			return "";
		}
	}

	@SideOnly(Side.CLIENT)
	public static abstract class ImageButton extends SonarButton {
		public final ResourceLocation texture;
		protected int textureX;
		protected int textureY;
		protected int sizeX, sizeY;
		public boolean bool;

		protected ImageButton(int id, int x, int y, ResourceLocation texture, int textureX, int textureY, int sizeX, int sizeY) {
			super(id, x, y, sizeX, sizeY, "");
			this.texture = texture;
			this.textureX = textureX;
			this.textureY = textureY;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int x, int y, float partialTicks) {
			if (this.visible) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                /*short short1 = 219;
				int k = 0;

				if (!this.enabled) {
					k += this.width * 2;
				} else if (this.bool) {
                    k += this.width;
				} else if (this.hovered) {
					k += this.width * 3;
                }*/

				mc.getTextureManager().bindTexture(texture);

                this.drawTexturedModalRect(this.x, this.y, this.textureX, this.textureY, sizeX + 1, sizeY + 1);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static abstract class AnimatedButton extends SonarButton {
		protected final ResourceLocation texture;
		protected final int sizeX, sizeY;

		protected AnimatedButton(int id, int x, int y, ResourceLocation texture, int sizeX, int sizeY) {
			super(id, x, y, sizeX, sizeY, "");
			this.texture = texture;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
		}

		public abstract int getTextureX();

		public abstract int getTextureY();

        @Override
        public void drawButton(Minecraft mc, int x, int y, float partialTicks) {
			if (this.visible) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
                /*short short1 = 219;
				int k = 0;

				if (!this.enabled) {
					k += this.width * 2;
				} else if (this.bool) {
                    k += this.width;
				} else if (this.hovered) {
					k += this.width * 3;
                }*/

				mc.getTextureManager().bindTexture(texture);

                this.drawTexturedModalRect(this.x, this.y, getTextureX(), getTextureY(), sizeX + 1, sizeY + 1);
			}
		}
	}
}
