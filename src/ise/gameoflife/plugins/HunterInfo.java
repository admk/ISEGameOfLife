/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ise.gameoflife.plugins;

import ise.gameoflife.environment.PublicEnvironmentConnection;
import ise.gameoflife.participants.PublicAgentDataModel;
import ise.gameoflife.tokens.TurnType;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import presage.Plugin;
import presage.Simulation;


/**Display useful info about each hunter
 *
 * @author The0s
 */
public class HunterInfo extends JPanel implements Plugin
{
	private static final long serialVersionUID = 1L;

	private class HunterPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		//private final XYSeries foodHistorySeries;
		private final PublicAgentDataModel dm;
		//private final ValueAxis domainAxis;
		//private final ValueAxis rangeAxis;

		private JLabel labelise(String s)
		{
			JLabel ret = new JLabel(s);
			ret.setHorizontalAlignment(SwingConstants.CENTER);
			ret.setFont(ret.getFont().deriveFont(6));
			return ret;
		}
                private JLabel labelise(String s,int font)
		{
			JLabel ret = new JLabel(s);
			ret.setHorizontalAlignment(SwingConstants.CENTER);
			ret.setFont(ret.getFont().deriveFont(font));
			return ret;
		}

		@SuppressWarnings("LeakingThisInConstructor")
		HunterPanel(PublicAgentDataModel dm)
		{
			this.dm = dm;

                        
                        String current = "Alive";
                        if (!sim.isParticipantActive(dm.getId()) ){
                            current = "Dead";
                        }
                        else if(dm.getGroupId() != null)
                        {
                            String Leader="";
                            //Leaders
                            if (!PublicEnvironmentConnection.getInstance().getGroupById(this.dm.getGroupId()).getPanel().isEmpty()){
                                for (String ldr : PublicEnvironmentConnection.getInstance().getGroupById(this.dm.getGroupId()).getPanel()){
                                        if (ldr.equals(this.dm.getId())){
                                            Leader = " - Leader";
                                        }
                                 }
                            }
                            current = "Alive - " + ec.getGroupById(dm.getGroupId()).getName() + Leader;
                        }
                        else{
                            current = "Alive - Free";
                        }
			//this.foodHistorySeries = new XYSeries(dm.getId());

			//JFreeChart chart = ChartFactory.createXYLineChart(null, null, null,
						//	new XYSeriesCollection(foodHistorySeries),
						//	PlotOrientation.VERTICAL, false, false, false);
			//ChartPanel chartPanel = new ChartPanel(chart);

			//chart.getXYPlot().setBackgroundAlpha(1);
			//domainAxis = chart.getXYPlot().getDomainAxis();
			//rangeAxis = chart.getXYPlot().getRangeAxis();

                        String food= Double.toString(this.dm.getFoodAmount());
                        String Loyalty = "Null";
                        String Happiness = "Null";
                        if (this.dm.getGroupId() != null ){ //exist only in groups
                            Loyalty= Double.toString(this.dm.getCurrentLoyalty());
                            Happiness = Double.toString(this.dm.getCurrentHappiness());
                        }
                        String Social = Double.toString(this.dm.getSocialBelief());
                        String Economic = Double.toString(this.dm.getEconomicBelief());
                        String LastHunted = "Null";
                        if (this.dm.getTime() > 4 && this.dm.getLastHunted() != null ){
                            LastHunted = this.dm.getLastHunted().getName();
                        }

                        JPanel dataPanel = new JPanel(new GridLayout(3, 3, 1, -1));

                        dataPanel.add(labelise(dm.getName(),8));
			dataPanel.add(labelise(dm.getPlayerClass()));
                        dataPanel.add(labelise(current));

			dataPanel.add(labelise("Food: "+food));
                        dataPanel.add(labelise("Economic: "+Economic));
                        dataPanel.add(labelise("Social: "+Social));

                        dataPanel.add(labelise("Loyalty: "+Loyalty));
			dataPanel.add(labelise("Happiness: "+Happiness));
                        dataPanel.add(labelise("LastHunted: "+LastHunted));



			//chartPanel.setVisible(true);

			this.setLayout(new GridLayout(1,1));
			this.add(dataPanel);
			//this.add(chartPanel);
			this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			window.add(this);
			this.setPreferredSize(new Dimension(getWidth() - barWidth, 125));
		}

		void updateData()
		{
			//if (dm.getFoodAmount() > graphHeight) graphHeight += 25;
			//foodHistorySeries.addOrUpdate(ec.getRoundsPassed(), dm.getFoodAmount());
			//domainAxis.setRange(ec.getRoundsPassed() - 25, ec.getRoundsPassed());
			//rangeAxis.setRange(0, graphHeight);


                }
	}

	private final static String label = "Hunter Logs";

	private Simulation sim;
	private PublicEnvironmentConnection ec = null;

	private final JPanel window = new JPanel();
	private final JScrollPane pane = new JScrollPane(window, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	//private final HashMap<String, HunterPanel> panels = new HashMap<String, HunterPanel>();
        private final TreeMap<String, HunterPanel> panels = new TreeMap<String, HunterPanel>();
	private int barWidth;
	private int graphHeight = 50;

	public HunterInfo()
	{
		// Nothing to see here. Move along, citizen!
	}

	@Override
	public void execute()
	{
		if (ec == null) ec = PublicEnvironmentConnection.getInstance();
		if (ec.getCurrentTurnType() != TurnType.firstTurn) return;

		barWidth = this.pane.getVerticalScrollBar().getWidth();
                panels.clear();
                //this.removeAll();
                this.window.removeAll();
                
                TreeMap<String, String> name_id_map = new TreeMap<String, String>();

                // Create a set sorted alphabetically by human readable name
		for (String aid : sim.getactiveParticipantIdSet("hunter"))
		{
                        name_id_map.put(ec.getAgentById(aid).getName(), aid);
		}

                // Add panels in alphabetical order
                for (Map.Entry<String, String> entry : name_id_map.entrySet()) {
                        String aid = entry.getValue();
                        if (!panels.containsKey(aid))
			{
				panels.put(aid, new HunterPanel(ec.getAgentById(aid)));
                                panels.get(aid).updateData();
                        }
			
                }
		validate();


                this.repaint();

                //panels.clear();




	}

	@Override
	public void initialise(Simulation sim)
	{
		setLayout(new BorderLayout());
		this.sim = sim;
		window.setLayout(new BoxLayout(window, BoxLayout.PAGE_AXIS));
		this.add(pane);
	}

	@Deprecated
	@Override
	public void onDelete()
	{
		// Nothing to see here. Move along, citizen!
	}

	@Override
	public void onSimulationComplete()
	{
		// Nothing to see here. Move along, citizen!
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	@Override
	public String getShortLabel()
	{
		return label;
	}

}
