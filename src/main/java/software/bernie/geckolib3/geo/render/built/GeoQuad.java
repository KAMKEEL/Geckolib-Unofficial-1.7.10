package software.bernie.geckolib3.geo.render.built;

import net.minecraft.util.EnumFacing;
import net.geckominecraft.util.math.Vec3i;

import java.io.Serializable;

public class GeoQuad implements Serializable {
	private static final long serialVersionUID = 42L;
	public GeoVertex[] vertices;
	public final Vec3i normal;
	public EnumFacing direction;

       public GeoQuad(GeoVertex[] verticesIn, float u1, float v1, float uSize, float vSize, float texWidth,
                       float texHeight, Boolean mirrorIn, EnumFacing directionIn, Integer uvRotation) {
               this.direction = directionIn;
               this.vertices = verticesIn;

		/*
		 * u1 is the distance from the very left of the texture to where the uv region
		 * starts v1 is the distance from the very top of the texture to where the uv
		 * region starts u2 is the horizontal distance from u1 to where the uv region
		 * ends v2 is the vertical distance from the v1 to where the uv region ends
		 */

		float u2 = u1 + uSize;
		float v2 = v1 + vSize;

		// Normalize the coordinates to be relative (between 0 and 1)
		u1 /= texWidth;
		u2 /= texWidth;
		v1 /= texHeight;
		v2 /= texHeight;

		// u1, v1 - Top left corner of uv region
		// u2, v1 - Top right corner of uv region
		// u1, v2 - Bottom left corner of uv region
		// u2, v2 - Bottom right corner of uv region

               // Sets the new normalized texture coordinates of each vertex using the
               // positions described above
               float[][] coords = new float[4][2];
               if (mirrorIn != null && mirrorIn) {
                       coords[0] = new float[] { u1, v1 };
                       coords[1] = new float[] { u2, v1 };
                       coords[2] = new float[] { u2, v2 };
                       coords[3] = new float[] { u1, v2 };
               } else {
                       coords[0] = new float[] { u2, v1 };
                       coords[1] = new float[] { u1, v1 };
                       coords[2] = new float[] { u1, v2 };
                       coords[3] = new float[] { u2, v2 };
               }

               float du = u2 - u1;
               float dv = v2 - v1;
               int rot = uvRotation == null ? 0 : ((uvRotation % 4) + 4) % 4;
               for (int i = 0; i < 4; i++) {
                       float s = du == 0 ? 0 : (coords[i][0] - u1) / du;
                       float t = dv == 0 ? 0 : (coords[i][1] - v1) / dv;
                       float sr = s;
                       float tr = t;
                       switch (rot) {
                               case 1:
                                       sr = 1 - t;
                                       tr = s;
                                       break;
                               case 2:
                                       sr = 1 - s;
                                       tr = 1 - t;
                                       break;
                               case 3:
                                       sr = t;
                                       tr = 1 - s;
                                       break;
                               default:
                                       break;
                       }
                       float u = u1 + sr * du;
                       float v = v1 + tr * dv;
                       vertices[i] = verticesIn[i].setTextureUV(u, v);
               }

		// only god knows what this does, but eliot told me it generates a normal vector
		// which helps the game do lighting properly or something idk i didnt pay
		// attention in physics we were in remote learning gimme a break
		this.normal = new Vec3i(directionIn.getFrontOffsetX(),directionIn.getFrontOffsetY(),directionIn.getFrontOffsetZ());
	}

       public GeoQuad(GeoVertex[] verticesIn, double[] uvCoords, double[] uvSize, float texWidth, float texHeight,
                       Boolean mirrorIn, EnumFacing directionIn, Integer uvRotation) {
               this(verticesIn, (float) uvCoords[0], (float) uvCoords[1], (float) uvSize[0], (float) uvSize[1], texWidth,
                               texHeight, mirrorIn, directionIn, uvRotation);
       }
}
