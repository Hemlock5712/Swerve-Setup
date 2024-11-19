# Vision

Similar to focusing the template on CTR Electronics or vision code focusing on using LimeLights. PhotonVision will still work but code structure should change slightly if that is your main method (no need for any MT2 inputs). 

<note>
    Due to there be many different hardware configurations, camera placements, AprilTag layouts, etc. 
This is not the end all be all. Make sure to experiment yourself!!!
</note>

Inside of code this year we have three different strategies added (and a `None` option). Bellow is list and what data they use

<list type="decimal" start="1">
<li>Mechanical Advantage (Recommended) 
<list type="alpha-lower">
<li>Number of tags (More tags = More trust)</li>
<li>Average Distance (Closer = More trust)</li>
</list>
</li>
<li>Cheesy Poofs (Being added)
<list type="alpha-lower">
<li>Multi-tag or Single-tag (two and three tags are trusted the same)</li>
<li>Average Area of Tags (based on area of tags seen)</li>
<li>MT1 or MT2 (MT2 is trusted more than MT1)</li>
</list>
</li>
<li>Beta - Currently being developed
<list type="alpha-lower">
<li>Uses all information from Cheesy Poofs but scales based on STD Devs provided by LL. 
This should ideally make the formula more robust for a new game instead of having hard coded values</li>
</list>
</li>
</list>

<warning>As of right now replay is not fully working as @AutoLog for records is not supported. It will be in Beta</warning>

Code methods can be found inside `VisionUtil.java`. 
This current setup allows us to quickly change existing or add new custom methods. 
We then can easily run changes in Replay to see how changes would have effected vision during the match.