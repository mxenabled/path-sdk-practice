# frozen_string_literal: true

class AccountsController < ApplicationController
  before_action :validate_header

  def index
    render json: [
      {
        id: 'acct-123',
        desc: 'Checking Account',
        t: 'CHK',
        bal: (rand * 10_000).round(2)
      }
    ]
  end

  def get
    render json: {
      id: 'acct-123',
      desc: 'Checking Account',
      t: 'CHK',
      bal: (rand * 10_000).round(2)
    }
  end

  private

  def validate_header
    return render status: :unauthorized unless request.headers['token'].present?

    true
  end
end
