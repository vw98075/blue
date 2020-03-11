import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>&copy; Vernon Wu, March 10, 2020</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
