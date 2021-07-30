import {React} from 'react';
import {Link} from 'react-router-dom';

import './TeamTile.scss';

function TeamTile({teamName}){


    return (

        <div className="TeamTile">
            <h1>
                <Link to={`/team/${teamName}`}>
                    {teamName}
                </Link>
            </h1>

        </div>

    );

}
export default TeamTile;