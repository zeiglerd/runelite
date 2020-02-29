/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import java.awt.*;
import java.awt.geom.Arc2D;

public enum UserInterfaceArcs
{
    ONE(400, 250, 20, 90f),
    TWO(400, 250, 30, 90f),
    THREE(400, 250, 40, 90f),
    FOUR(400, 250, 50, 90f),
    FIVE(400, 250, 60, 90f);

    int x;
    int y;
    int diameter;
    float start;

    UserInterfaceArcs(final int x, final int y, final int diameter, final float start)
    {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.start = start;
    }

    static void drawArcs(PickPocketPlugin p, Graphics2D g)
    {
        java.awt.Point uiLocation = PickPocketHelper.getUiLocation(p, g);
        if (!p.isUiEnabled() || uiLocation == null
                || (uiLocation.x == -1 && uiLocation.y == -1))
        {
            return;
        }

        final UserInterfaceArcs[] arcs = UserInterfaceArcs.values();
        final UserInterface[] ui = UserInterface.getOrder();

        PickPocketHelper.drawArc(p, g, arcs, 90f, 360f,
                new Color(0, 0, 0,
                        (int) (Float.parseFloat(p.getConfig().backgroundInterfaceOpacity()) * 255)
                ), uiLocation, 6f, true);

        for (int uiIdx = 0, arcIdx = 0; uiIdx < ui.length; uiIdx++)
        {
            if (arcs[arcIdx] == null)
            {
                break;
            }

            final float extent = ui[uiIdx].extent.apply(p);
            if (extent == -1)
            {
                continue;
            }

            UserInterfaceArcs def = arcs[arcIdx];
            PickPocketHelper.drawArc(p, g, def.diameter, def.start, extent,
                    ui[uiIdx].color.apply(p), uiLocation, 6f, false);

            arcIdx++;
        }
    }
}


////					if (config.displayDodgyNecklaceOnInterface())
////					{
////						continue;
////					}
////					percent = config.dodgyNecklaceCharge() / 12.;
//
//
//		if (config.displayPlayerHitpointsOnInterface())
//                {
//                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//                BasicStroke wideStroke = new BasicStroke(24f);
//                g.setStroke(wideStroke);
//
//                double percent = client.getBoostedSkillLevel(Skill.HITPOINTS) / 99.;
//
////			RadialGradientPaint gradient = new RadialGradientPaint(
////
////			);
//
//                LinearGradientPaint gradient = new LinearGradientPaint(
//                new Point2D.Float(config.point1X(), config.point1Y()),
//                new Point2D.Float(config.point2X(), config.point1Y() / 100),
////					new float[] { 0f, 0.25f, 1f },
////					new Color[] { Color.red, Color.yellow, Color.green }
//                new float[] {
//                0f,
//                0.05f, 0.15f,
////						0.3f, 1f
//                },
//                new Color[] {
////						Color.green,
////						Color.yellow,
//                Color.red,
//                Color.yellow,
//                Color.green
//                }
////					new float[] {
////							0f,
////							0.20f,
////							0.68f, 0.69f,
////							0.70f, 1f
////					},
////					new Color[] {
////							new Color(255, 0, 0),
////							new Color(255, 255, 0),
////							new Color(0, 255, 0), new Color(0, 255, 0),
////							new Color(0, 0, 0, 0), new Color(0, 0, 0, 0) }
//                , CycleMethod.NO_CYCLE);
//
//                g.setPaint(gradient);
//                g.draw(new Arc2D.Double(
//                200., 200., 200., 200., 90., -360. * percent, Arc2D.OPEN));
//
////			g.setColor(Color.GREEN);
////			g.draw(new Arc2D.Double(25, 25, 200, 200, 180,
////					, Arc2D.OPEN));
//
//////			final Rectangle bounds = new Rectangle(400, 200, 60, 60);
////			final Rectangle bounds = new Rectangle(config.overlayX(), config.overlayY(),
////					config.overlayDiameter(), config.overlayDiameter());
////
////			g.setPaint(new LinearGradientPaint(
//////					new Point2D.Float(bounds.x, bounds.y),
//////					new Point2D.Float(bounds.x, bounds.y + bounds.height),
////					new Point2D.Float(config.point1X(), config.point1Y()),
////					new Point2D.Float(config.point2X(), config.point2Y()),
//////					new float[] {
////////							0f, 0.15f,
////////							0.25f, 0.50f,
////////							0.75f, 1f
////////					},
////					new float[] {
////							config.gradientFloat1() * .01f, config.gradientFloat2() * .01f,
////							config.gradientFloat3() * .01f, config.gradientFloat4() * .01f,
////							config.gradientFloat5() * .01f, config.gradientFloat6() * .01f
////					},
////					new Color[] {
////							Color.RED, Color.GREEN,
////							Color.BLUE, Color.YELLOW,
////							Color.PINK, Color.MAGENTA
////					}));
////			g.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
////			g.draw(new Arc2D.Double(
////					bounds.x,
////					bounds.y + (bounds.height / 2 - 60. / 2),
////					60.,
////					60.,
////					90.,
////					360.,
////					Arc2D.OPEN));
////
////			g.setColor(Color.GREEN);
////			g.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
////			g.draw(new Arc2D.Double(
////					bounds.x,
////					bounds.y + (bounds.height / 2 - 60. / 2) + 70, // @todo remove 70
////					60.,
////					60.,
////					90.,
////					-360. * (client.getBoostedSkillLevel(Skill.HITPOINTS) / 99.),
////					Arc2D.OPEN));
//
////			g.setPaint(ring.color);
////			g.fill(arc);
////
////			g.setColor(new Color(0x9B0703));
////			g.draw(arc);
//                }
//
//
//
//
////			Arc2D.Double arc = new Arc2D.Double(
////					ring.x,// + OFFSET,
////					ring.y,// + (bounds.height / 2 - DIAMETER / 2),
////					ring.diameter,
////					ring.diameter,
////					90.,
////					-360. * percent,
////					Arc2D.OPEN);
////
//////				final Stroke STROKE = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
//////
//////				g.setStroke(STROKE);
//////				g.setColor(new Color(0x9B0703));
////			g.setPaint(ring.color);
////			g.draw(arc);
//////				g.fill(arc);
////		}
//
//
//
//
//
//
