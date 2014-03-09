package org.codehaus.mojo.jdepend;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Run JDepend and generate a site report. Goal which generate the JDepend metrics.
 * 
 * @author aramirez@exist.com
 * @version $Id$
 */
@Mojo( name = "generate", requiresProject = true )
@Execute( phase = LifecyclePhase.COMPILE )
public class JDependMojo
    extends AbstractJDependMojo
{
}
