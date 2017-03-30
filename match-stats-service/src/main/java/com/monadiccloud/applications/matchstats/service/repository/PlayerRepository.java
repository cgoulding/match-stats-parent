/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.monadiccloud.applications.matchstats.service.repository;

import com.monadiccloud.applications.matchstats.service.model.Player;

import java.util.Collection;

/**
 * @author Connor Goulding
 */
public interface PlayerRepository
{
    Collection<Player> searchPlayer(String term);
}
